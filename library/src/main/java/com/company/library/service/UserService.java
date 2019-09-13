package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.enums.Direction;
import com.company.library.enums.OrderBy;
import com.company.library.exceptions.EmailExistsException;
import com.company.library.exceptions.PaginationSortingException;
import com.company.library.model.Penalty;
import com.company.library.model.ResponsePageList;
import com.company.library.model.Role;
import com.company.library.model.User;
import com.company.library.repository.RoleRepository;
import com.company.library.repository.UserRepositoryInterface;


import org.apache.tomcat.jni.Local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class UserService implements UserServiceInterface {

    private final PasswordEncoder bcryptEncoder;
    private final RoleRepository roleRepository;
    private final UserRepositoryInterface userRepository;

    @Autowired
    public UserService(PasswordEncoder bcryptEncoder, RoleRepository roleRepository, UserRepositoryInterface userRepository) {
        this.bcryptEncoder = bcryptEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    private UserBookServiceInterface userBookService;


    @Autowired
    private UserRepositoryInterface userDao;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User registerNewUserAccount(Registration userDto) throws EmailExistsException {
        if (emailExists(userDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            + userDto.getEmail());
        }

        User newUser = new User();

        if (userDto.getEmail().equals("librarymaster0@gmail.com") && userDto.getPassword().equals("qwerty1234.")) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            newUser.setFirstName(userDto.getFirstName());
            newUser.setLastName(userDto.getLastName());
            newUser.setEmail(userDto.getEmail());
            newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
            newUser.setRoles(Collections.singleton(adminRole));
            newUser.setAdmin(true);
        } else {
            Role userRole = roleRepository.findByName("ROLE_USER");
            newUser.setFirstName(userDto.getFirstName());
            newUser.setLastName(userDto.getLastName());
            newUser.setEmail(userDto.getEmail());
            newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
            newUser.setRoles(Collections.singleton(userRole));
            newUser.setAdmin(false);
        }

        return userRepository.save(newUser);
    }


    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean emailExists(final String email) {
        com.company.library.model.User foundUser = userRepository.findByEmail(email);
        System.out.println(foundUser);
        return (userRepository.findByEmail(email) != null);
    }


    public User saveNewPassword(String email, String password) {
        User foundUser = userRepository.findByEmail(email);
        foundUser.setPassword(bcryptEncoder.encode(password));
        return userDao.save(foundUser);
    }

    @Override
    public ResponsePageList<User> findPaginatedUsers(String orderBy, String direction, int page, int size, String query) {
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        }
        if (direction.equals("DESC")) {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        }

        if (!(direction.equals(Direction.ASCENDING.getDirectionCode()) || direction.equals(Direction.DESCENDING.getDirectionCode()))) {
            throw new PaginationSortingException("Invalid sort direction");
        }
        if (!(orderBy.equals(OrderBy.ID.getOrderByCode()) || orderBy.equals(OrderBy.TITLE.getOrderByCode()))) {
            throw new PaginationSortingException("Invalid orderBy condition");
        }

        Predicate<User> firstNameExist = user -> user.getFirstName().toLowerCase().contains(query.toLowerCase());
        Predicate<User> lastNameExist = user -> user.getLastName().toLowerCase().contains(query.toLowerCase());
        List<User> list = userRepository.findAll(sort).stream().filter(firstNameExist.or(lastNameExist)).collect(Collectors.toList());

        PagedListHolder<User> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);
        ResponsePageList<User> response = new ResponsePageList<>();
        response.setNrOfElements(pagedListHolder.getNrOfElements());
        response.setPageList(pagedListHolder.getPageList());
        return response;

    }

    @Override
    public void clearPenalties(User u) {
        //find the user and search for old penalties to remove them
        userRepository.findById(u.getId()).ifPresent(t->t.getPenalties().forEach(p-> {
            if(p.getPenaltyAddedDate().isBefore(LocalDate.now()))
                t.getPenalties().remove(p);
        }));
    }

    @Override
    public void checkForPenalties(User user) {
        userBookService.getUserBooks().stream().filter(t->t.getUser().getId().equals(user.getId())).forEach(t->{
            System.out.println(t);
            if(!t.isGeneratedPenalty() && t.getReturn_date().isBefore(LocalDate.now())) {
                t.getUser().addPenalty(new Penalty(LocalDate.now().plusMonths(Penalty.numberOfMonthsPenaltyExist)));
                System.out.println(t.isGeneratedPenalty());
                userBookService.changeUserBookPenalty(t.getId());
            }
        });
    }
}
