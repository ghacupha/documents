package io.github.docs.repository;

import io.github.docs.domain.UserProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserProfile entity.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {

    @Query(value = "select distinct userProfile from UserProfile userProfile left join fetch userProfile.transactionDocuments left join fetch userProfile.formalDocuments",
        countQuery = "select count(distinct userProfile) from UserProfile userProfile")
    Page<UserProfile> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userProfile from UserProfile userProfile left join fetch userProfile.transactionDocuments left join fetch userProfile.formalDocuments")
    List<UserProfile> findAllWithEagerRelationships();

    @Query("select userProfile from UserProfile userProfile left join fetch userProfile.transactionDocuments left join fetch userProfile.formalDocuments where userProfile.id =:id")
    Optional<UserProfile> findOneWithEagerRelationships(@Param("id") Long id);

}
