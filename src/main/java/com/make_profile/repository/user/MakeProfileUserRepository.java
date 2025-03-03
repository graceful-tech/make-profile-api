package com.make_profile.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.make_profile.entity.user.MakeProfileUserEntity;

@Repository
public interface MakeProfileUserRepository extends JpaRepository<MakeProfileUserEntity, Long> {

}
