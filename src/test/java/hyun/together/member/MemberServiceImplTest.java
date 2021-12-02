package hyun.together.member;

import hyun.together.AppConfig;
import hyun.together.domain.Member;
import hyun.together.repository.member.MemberRepository;
import hyun.together.repository.member.MemoryMemberRepository;
import hyun.together.service.member.MemberService;
import hyun.together.service.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceImplTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    @BeforeEach
    public void beforeEach(){
        memberRepository=new MemoryMemberRepository();
        memberService=new MemberServiceImpl(memberRepository);
    }

    @AfterEach
    public void afterEach(){
         memberRepository.clearStore();
    }

    @Test
    void 회원가입(){
        //given
        Member member=new Member();
        member.setId("aaaa");
        member.setName("김철수");
        member.setPassword("1234");
        member.setEmail("aaaa@naver.com");

        //when
        String saveId=memberService.join(member);

        //then
        Member findMember=memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복회원(){

        //given
        Member member1=new Member();
        member1.setId("aaaa");
        member1.setName("김철수");
        member1.setPassword("1234");
        member1.setEmail("aaaa@naver.com");

        Member member2=new Member();
        member2.setId("aaaa");
        member2.setName("홍길동");
        member2.setPassword("4567");
        member2.setEmail("bbbb@naver.com");


        //when
        memberService.join(member1);
        IllegalStateException e= assertThrows(IllegalStateException.class,()->memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
        
        /*
        Member member1=new Member();
        member1.setId("aaaa");
        member1.setName("김철수");
        member1.setPassword("1234");
        member1.setEmail("aaaa@naver.com");

        Member member2=new Member();
        member2.setId("bbbb");
        member2.setName("김철수");
        member2.setPassword("1234");
        member2.setEmail("aaaa@naver.com");

        String saveId1 = memberService.join(member1);
        String saveId2 = memberService.join(member2);

        Member findMember1=memberService.findOne(saveId1).get();
        Member findMember2=memberService.findOne(saveId2).get();

        assertThat(findMember1.getEmail()).isEqualTo(findMember2.getEmail());
        
         */
    }
    
    @Test
    void 이메일로_아이디_찾기(){
        Member member=new Member();
        member.setId("aaaa12");
        member.setName("김민수");
        member.setPassword("12345*");
        member.setEmail("aaaa34@naver.com");
        
        String saveId=memberService.join(member);
        String findId=memberService.findId("aaaa34@naver.com");

        assertThat(findId).isEqualTo(member.getId());
    }

    @Test
    void 아이디로_비밀번호_찾기(){
        Member member=new Member();
        member.setId("aaaa12");
        member.setName("김민수");
        member.setPassword("12345*");
        member.setEmail("aaaa34@naver.com");

        String saveId=memberService.join(member);
        String findPassword=memberService.findPassword("aaaa12");

        assertThat(findPassword).isEqualTo(member.getPassword());
    }

    @Test
    void 회원탈퇴(){
        Member member=new Member();
        member.setId("aaaa12");
        member.setName("김민수");
        member.setPassword("12345*");
        member.setEmail("aaaa34@naver.com");

        String saveId=memberService.join(member);
        Member findMember1=memberService.findOne(saveId).get();
        assertThat(member.getId()).isEqualTo(findMember1.getId());

        //회원 탈퇴 실패(비번 틀림)
        memberService.withdraw("aaaa12","1234");
        Member findMember2=memberService.findOne(member.getId()).get();
        assertThat(member.getId()).isEqualTo(findMember2.getId());

        //회원 탈퇴 성공
        memberService.withdraw("aaaa12","12345*");
        assertThrows(NoSuchElementException.class,()->memberService.findOne(saveId).get());

    }
}
