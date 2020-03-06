package io.github.docs.app;

import io.github.docs.service.UserProfileQueryService;
import io.github.docs.service.UserService;
import io.github.docs.service.dto.TransactionDocumentDTO;
import io.github.docs.service.dto.UserDTO;
import io.github.docs.service.dto.UserProfileCriteria;
import io.github.docs.service.dto.UserProfileDTO;
import io.github.docs.service.mapper.TransactionDocumentMapper;
import io.github.docs.service.mapper.UserMapper;
import io.github.docs.service.mapper.UserProfileMapper;
import io.github.jhipster.service.filter.LongFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocumentIntercept {

    private final UserService userService;
    private final UserMapper userMapper;
    private final TransactionDocumentMapper transactionDocumentMapper;
    private final UserProfileMapper userProfileMapper;
    private final UserProfileQueryService userProfileQueryService;

    public DocumentIntercept(final UserService userService, final UserMapper userMapper, final TransactionDocumentMapper transactionDocumentMapper, final UserProfileMapper userProfileMapper,
                             final UserProfileQueryService userProfileQueryService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.transactionDocumentMapper = transactionDocumentMapper;
        this.userProfileMapper = userProfileMapper;
        this.userProfileQueryService = userProfileQueryService;
    }

    public ResponseEntity<TransactionDocumentDTO> intercept(final ResponseEntity<TransactionDocumentDTO> responseEntity) {
        UserDTO currentUser = userMapper.userToUserDTO(userService.getUserWithAuthorities().get());
        UserProfileCriteria userProfileCriteria = new UserProfileCriteria();
        LongFilter userIdFilter = new LongFilter();
        userIdFilter.setEquals(currentUser.getId());
        userProfileCriteria.setUserId(userIdFilter);

        // Add this document to all profiles of this User
        List<UserProfileDTO> userProfiles = userProfileQueryService.findByCriteria(userProfileCriteria);
        userProfiles.forEach(profile -> userProfileMapper.toEntity(profile).addTransactionDocuments(transactionDocumentMapper.toEntity(responseEntity.getBody())));

        return responseEntity;
    }
}
