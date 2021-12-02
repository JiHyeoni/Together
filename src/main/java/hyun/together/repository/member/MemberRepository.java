package hyun.together.repository.member;

import hyun.together.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);//메모리에 저장
    Optional<Member> findById(String id); //아이디로 찾기
    Optional<Member> findByName(String name); //이름으로 찾기
    Optional<Member> findByEmail(String email); //이메일로 찾기
    Member delete(Member member); //회원 탈퇴

}
