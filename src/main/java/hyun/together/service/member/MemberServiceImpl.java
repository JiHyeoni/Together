package hyun.together.service.member;

import hyun.together.domain.Member;
import hyun.together.repository.member.MemberRepository;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public String join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //같은 아이디의 중복 회원 X
        Optional<Member> result=memberRepository.findById(member.getId());
        result.ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        });
    }

    @Override
    public Optional<Member> findOne(String memberId){
        return memberRepository.findById(memberId);
    }

    @Override
    public String findId(String memberEmail) {
        Optional<Member> member=memberRepository.findByEmail(memberEmail);
        Member findMember=member.orElse(null);
        if(findMember!=null) return findMember.getId();
        else return null;
    }

    @Override
    public String findPassword(String memberId) {
        Optional<Member> member=memberRepository.findById(memberId);
        Member findMember=member.orElse(null);
        if(findMember!=null) return findMember.getPassword();
        else return null;
    }

    @Override
    public void withdraw(String memberId, String memberPassword) {
        Optional<Member> member = memberRepository.findById(memberId);
        Member findMember=member.orElse(null);
        if(findMember!=null)
            if (findMember.getPassword()==memberPassword)
                memberRepository.delete(findMember);
    }
    //Optional<Member>로 하는 방법과 null시 오류창 뜨는 방법 알아보기!
}
