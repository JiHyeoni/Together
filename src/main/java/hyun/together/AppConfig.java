package hyun.together;

import hyun.together.repository.member.MemberRepository;
import hyun.together.repository.member.MemoryMemberRepository;
import hyun.together.service.member.MemberService;
import hyun.together.service.member.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
