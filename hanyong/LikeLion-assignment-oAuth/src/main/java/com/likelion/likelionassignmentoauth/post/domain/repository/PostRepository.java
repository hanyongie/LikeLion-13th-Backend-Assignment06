package com.likelion.likelionassignmentoauth.post.domain.repository;

import com.likelion.likelionassignmentoauth.post.domain.Post;
import com.likelion.likelionassignmentoauth.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMember(Member member);
}
