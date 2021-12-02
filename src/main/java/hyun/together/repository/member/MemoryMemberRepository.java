package hyun.together.repository.member;

import hyun.together.domain.Member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<String,Member> store=new HashMap<>();

    @Override
    public Member save(Member member) {
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Member delete(Member member) {
        store.remove(member.getId());
        return member;
    }

    public void clearStore(){
        store.clear();
    }
}
