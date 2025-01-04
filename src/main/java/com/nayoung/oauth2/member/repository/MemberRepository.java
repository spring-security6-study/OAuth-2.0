package com.nayoung.oauth2.member.repository;

import com.nayoung.oauth2.member.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, String> {

	Optional<Member> findByEmail(String email);
}
