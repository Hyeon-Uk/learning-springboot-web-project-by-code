package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemoRepositoryTest {
    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("Sample..." + i)
                    .build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("=============================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println("memo = " + memo);
        } else {
            System.out.println("not present");
        }
    }

    @Test
    @Transactional
    public void testSelectGetOne() {
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("==============");

        System.out.println("memo = " + memo);
    }

    @Test
    public void testUpdate() {
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("Update Text")
                .build();

        memoRepository.save(memo);
    }

    @Test
    public void testDelete() {
        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageRequest);

        System.out.println(result);

        System.out.println("==================================");

        System.out.println("Total Pages : " + result.getTotalPages());

        System.out.println("Total Count : " + result.getTotalElements());

        System.out.println("Page Number : " + result.getNumber());

        System.out.println("Page Size : " + result.getSize());

        System.out.println("Has Next Page ? : " + result.hasNext());

        System.out.println("Has Previous Page ? : " + result.hasPrevious());

        System.out.println("Is First Page ? : " + result.isFirst());

        result.getContent().stream().forEach(System.out::println);
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();

        PageRequest pageRequest1 = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageRequest1);

        result.get().forEach(System.out::println);
    }

    @Test
    public void testQueryMethod() {
        List<Memo> result = memoRepository.findByMnoBetweenOrderByMnoDesc(70l, 80l);
        result.stream().forEach(System.out::println);
    }

    @Test
    public void testQueryMethodWithPageable(){
        PageRequest pageRequest = PageRequest.of(0,10,Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageRequest);

        result.get().forEach(System.out::println);
    }

    @Test
    @Transactional
    @Commit
    public void testDeleteQueryMethods(){
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

    @Test
    public void testUpdateWithQuery(){
        Long mno = 99L;
        String updateMemoText = "Update MemoText1";

        int result = memoRepository.updateMemoText(mno, updateMemoText);
        System.out.println("result = " + result);
    }

    @Test
    public void testUpdateWithQuery2(){
        Memo memo = Memo.builder()
                .mno(99l)
                .memoText("Update MemoText2")
                .build();

        int result = memoRepository.updateMemoText(memo);
        System.out.println("result = " + result);
    }

    @Test
    public void testGetListWithQuery(){
        PageRequest pageRequest = PageRequest.of(0,10,Sort.by("mno").descending());

        Page<Memo> result = memoRepository.getListWithQuery(30l, pageRequest);
        result.get().forEach(System.out::println);
    }

    @Test
    public void testGetListWithQuery2(){
        PageRequest pageRequest = PageRequest.of(0,10,Sort.by("mno").descending());

        Page<Object[]> result = memoRepository.getListWithQueryObject(30l, pageRequest);
        result.get().forEach(obj->{
            Arrays.stream(obj).forEach(System.out::println);
        });
    }

    @Test
    public void testFindAllWithNativeQuery(){
        List<Object[]> nativeResult = memoRepository.getNativeResult();

        nativeResult.stream().forEach(obj->{
            Arrays.stream(obj).forEach(System.out::println);
        });
    }
}
