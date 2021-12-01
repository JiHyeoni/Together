package hyun.together.service.member;

import hyun.together.domain.Member;
import hyun.together.repository.member.MemberRepository;

public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public String findId(String memberEmail) {
        Member member=memberRepository.findByEmail(memberEmail);
        return member.getId();
    }

    @Override
    public String findPassword(String memberId) {
        Member member=memberRepository.findById(memberId);
        return member.getPassword();
    }

    @Override
    public void withdraw(String memberId, String memberPassword) {
        Member member = memberRepository.findById(memberId);
        if (member.getPassword()==memberPassword) memberRepository.delete(member);
    }
    //Optional<Member>로 하는 방법과 null시 오류창 뜨는 방법 알아보기!
}
