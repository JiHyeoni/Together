package hyun.together.service.member;

import hyun.together.domain.Member;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemberService {
    String join(Member member); //회원가입
    Optional<Member> findOne(String memberId);//회원 조회(회원 찾기)
    String findId(String memberEmail); //회원 조회(아이디 찾기)
    String findPassword(String memberId); //회원 조회(비밀번호 찾기)
    void withdraw(String memberId, String memberPassword); //회원 탈퇴
}
