package org.zobic.ecommerceshopapi.service;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.dto.AddressDto;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.dto.UserDtoUpdate;
import org.zobic.ecommerceshopapi.event.NewTokenGeneratedEvent;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.exception.TokenExpiredException;
import org.zobic.ecommerceshopapi.model.Address;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.model.VerificationToken;
import org.zobic.ecommerceshopapi.repository.UserRepository;
import org.zobic.ecommerceshopapi.repository.VerificationTokenRepository;
import org.zobic.ecommerceshopapi.util.UtilitySecurity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

// add throwable exceptions
@Service
public class UserServiceImplementation implements UserService {
  private UtilitySecurity utilitySecurity;
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private RoleService roleService;
  private AddressService addressService;
  private EntityManager entityManager;
  private VerificationTokenRepository verificationTokenRepository;
  private ApplicationEventPublisher eventPublisher;



  public UserServiceImplementation(UtilitySecurity utilitySecurity, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, AddressService addressService, EntityManager entityManager, VerificationTokenRepository verificationTokenRepository, ApplicationEventPublisher eventPublisher) {
    this.utilitySecurity = utilitySecurity;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
    this.addressService = addressService;
    this.entityManager = entityManager;
    this.verificationTokenRepository = verificationTokenRepository;
    this.eventPublisher = eventPublisher;
  }

  @Transactional
  @Override
  public User registerUser(UserDto userDto) throws Exception {
    String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
    Address address = null;
    if (userDto.getAddress() != null) {
      address = this.addressService.save(userDto.getAddress());
    }
    User newUser = new User(userDto.getUsername(), encryptedPassword, userDto.getEmail(), Arrays.asList(this.roleService.findByTitle("ROLE_ADMIN")), address, false);
    return this.userRepository.registerUser(newUser);
  }

  @Transactional
  @Override
  public User updateUser(UserDtoUpdate userDto, UUID id) throws Exception {
    User userToUpdate = this.findUserById(id);

    UUID addressID = null;
    Address userToUpdateAddress = userToUpdate.getAddress();
    if (userToUpdateAddress != null) {
      addressID = userToUpdate.getId();
    }

    AddressDto addressDto = userDto.getAddress();
    if (addressDto != null) {
      Address updatedAddress = this.addressService.update(addressID, addressDto, userToUpdateAddress);
      userToUpdate.setAddress(updatedAddress);
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean isUserSelfModify = userToUpdate.getUsername().equals(authentication.getName());
    boolean isAdmin = utilitySecurity.userHasAdminRole();

    userToUpdate.setUsername(userDto.getUsername());
    userToUpdate.setEmail(userDto.getEmail());
    if (userDto.getPassword() != null) {
      userToUpdate.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
    }

    if (authentication.getName().equals(userToUpdate.getUsername()) && !isAdmin) {
      User user = this.userRepository.registerUser(userToUpdate);
      if (isUserSelfModify) {
        this.utilitySecurity.updateSecurityContext(user.getUsername());
      }
      return user;

    } else if (isAdmin) {

       if (userDto.getRole() != null) {
         userToUpdate.setRoles(new ArrayList<>(Arrays.asList(this.roleService.findByTitle(userDto.getRole()))));
       }

       if (userDto.getIsEnabled() != null) {
         userToUpdate.setEnabled(userDto.getIsEnabled());
       }
        User user = this.userRepository.registerUser(userToUpdate);

        if (isUserSelfModify) {
          this.utilitySecurity.updateSecurityContext(user.getUsername());
        }
        return user;
      }
    throw new Exception("User only has the permissions to update his own profile.");
  }

  @Override
  @Transactional
  public void deleteUser(UUID id) throws Exception {
    Session session = entityManager.unwrap(Session.class);
    Filter filter = session.enableFilter("deletedUserFilter");
    filter.setParameter("isDeleted", false);
    System.out.println(session.getEnabledFilter("deletedUserFilter").getFilterDefinition().getFilterName());
    User user = this.findUserById(id);
    System.out.println(user.getUsername());
    this.userRepository.deleteUser(id);
    session.disableFilter("deletedUserFilter");
  }

  @Override
  public User findUserById(UUID id) throws Exception {
    return this.userRepository.findUserById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
  }

  @Transactional
  @Override
  public User createUser(UserDto userDto) throws Exception {
    String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
    if (userDto.getRole() == null) {
      userDto.setRole("ROLE_USER");
    }
    Address address = null;
    if (userDto.getAddress() != null) {
      address = this.addressService.save(userDto.getAddress());
    }
    Boolean enabled = false;
    if (userDto.getIsEnabled() != null) {
      enabled = userDto.getIsEnabled();
    }
    User newUser = new User(userDto.getUsername(), encryptedPassword, userDto.getEmail(), Arrays.asList(this.roleService.findByTitle(userDto.getRole())), address, enabled);

    return this.userRepository.registerUser(newUser);
  }

  @Override
  public Optional<User> findUserByUsername(String username) {
    return this.userRepository.findUserByUsername(username);
  }

  @Override
  public Optional<User> findUserByEmail(String email) {
    return this.userRepository.findUserByEmail(email);
  }

  @Override
  public Iterable<User> findAllUsers() {
    return this.userRepository.findAllUsers();
  }

  @Override
  public Page<User> findAllUsersPageable(Pageable pageable) {
    return this.userRepository.findAllUsersPageable(pageable);
  }

  @Override
  public VerificationToken createVerificationToken(User user) {
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setUser(user);
    verificationToken.setExpiryDate(VerificationToken.calculateExpiryDate(VerificationToken.EXPIRATION));
    return this.verificationTokenRepository.save(verificationToken);
  }

  @Override
  @Transactional
  public void destroyOldAndCreateNewVerificationToken(UUID userId) throws Exception {
    User user = this.findUserById(userId);
    VerificationToken oldToken = this.verificationTokenRepository.findByUserId(userId);

    if (oldToken == null) {
      throw new ResourceNotFoundException("User has been already authenticated.");
    }

    VerificationToken newToken = new VerificationToken();
    newToken.setUser(user);
    newToken.setExpiryDate(VerificationToken.calculateExpiryDate(VerificationToken.EXPIRATION));
    this.verificationTokenRepository.delete(oldToken.getId());
    newToken = this.verificationTokenRepository.save(newToken);
    this.eventPublisher.publishEvent(new NewTokenGeneratedEvent(Locale.ENGLISH, user, newToken));
  }

  @Override
  public void confirmUser(UUID token) throws ResourceNotFoundException, TokenExpiredException {
    VerificationToken verificationToken = this.verificationTokenRepository.findById(token);

    User user = verificationToken.getUser();
    Calendar calendar = Calendar.getInstance();

    if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
      this.verificationTokenRepository.delete(token);
      throw new TokenExpiredException("The verification token has expired please request another one");
    }

    user.setEnabled(true);
    this.userRepository.registerUser(user);
    this.verificationTokenRepository.delete(token);
  }
}

