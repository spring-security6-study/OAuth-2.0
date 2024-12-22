package com.nayoung.oauth2.member.repository;

import com.nayoung.oauth2.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
