package io.github.docs.app;

import io.github.docs.service.UserService;
import io.github.docs.service.dto.UserDTO;
import io.github.docs.service.mapper.UserMapper;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * This resource is create to enhance user accounting at the front end. For instance
 * it might become mighty hard for one to know with which profile one is currently logged in
 * without first logging out and logging in just to be sure.
 *
 * The resource once called with return details of the current user from the UserService
 * hopefully if multiple users are logged on it will not create an Issue
 *
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CurrentUserResource {

    private final UserService userService;
    private final UserMapper userMapper;

    public CurrentUserResource(final UserService userService, final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * {@code GET /users/current} : get the current user.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users/current")
    public ResponseEntity<UserDTO> getUser() {
        log.debug("REST request to get current User");

        Optional<UserDTO> currentUser = Optional.of(userMapper.userToUserDTO(userService.getUserWithAuthorities().get()));

        return ResponseUtil.wrapOrNotFound(currentUser);
    }
}
