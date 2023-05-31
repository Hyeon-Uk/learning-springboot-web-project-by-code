package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoRepositoryTest {
    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("Sample...".concat(Integer.toString(i)))
                    .build();

            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 100l;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("====================================");
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {
        Long mno = 100l;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("====================================");

        System.out.println(memo);
    }

    @Test
    public void testUpdate() {
        Memo memo = Memo.builder()
                .mno(100l)
                .memoText("Update text")
                .build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete() {
        Long mno = 100l;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("====================================");

        System.out.println("result.getTotalPages() = " + result.getTotalPages());//총 몇페이지

        System.out.println("result.getTotalElements() = " + result.getTotalElements());//총 개수

        System.out.println("result.getNumber() = " + result.getNumber());//현재 페이지 번호

        System.out.println("result.getSize() = " + result.getSize());//페이지당 데이터 개수

        System.out.println("result.hasNext() = " + result.hasNext());//다음페이지 존재 여부

        System.out.println("result.hasPrevious() = " + result.hasPrevious());//이전페이지 존재 여부

        System.out.println("result.isFirst() = " + result.isFirst());//첫페이지인지

        System.out.println("result.isLast() = " + result.isLast());//마지막페이지인지

        System.out.println("====================================");

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
        System.out.println("====================================");
        Sort sort2 = Sort.by("memoText").ascending();

        pageable = PageRequest.of(0, 10, sort2);

        result = memoRepository.findAll(pageable);
        System.out.println("====================================");
        result.get().forEach(memo -> {
            System.out.println(memo);
        });

        Sort sortAll = sort1.and(sort2);

        pageable = PageRequest.of(0, 10, sortAll);
        result = memoRepository.findAll(pageable);
        System.out.println("====================================");
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70l, 90l);

        for(Memo memo : list){
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10l, 50l, pageable);

        result.get().forEach(System.out::println);
    }

    @Test
    @Transactional
    @Commit
    public void testDeleteQueryMethods(){
        memoRepository.deleteMemoByMnoLessThan(10l);
    }
}