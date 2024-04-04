package com.example.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

// final 붙이거나 @Autowired 로 사용하거나 둘 중 하나
// @Service
// public class MemoServiceImpl {
//     @Autowired
//     private MemoRepository memoRepository;
//
// private MemoRepository memoRepository = new MemoRepository();   = new ~ 를 스프링 컨테이너가 대신 해서 갖고있기 때문에 할 필요 x - 이걸 불러오는 게 @Autowired
// }

@Service
// @NonNull, final 이 붙은 멤버변수를 대상으로 생성자 생성
@RequiredArgsConstructor
public class MemoServiceImpl {
    private final MemoRepository memoRepository;

    // @RequiredArgsConstructor 가 대신 해주기 때문에 할 필요 x
    // public MemoServiceImpl (MemoRepository memoRepository) {
    // this.memoRepository = memoRepository
    // }

    public List<MemoDto> getMemoList() {
        List<Memo> entities = memoRepository.findAll();

        List<MemoDto> list = new ArrayList<>();
        entities.forEach(entity -> list.add(entityToDto(entity)));
        return list;
    }

    public MemoDto getMemo(Long mno) {
        Memo entity = memoRepository.findById(mno).get();
        return entityToDto(entity);
    }

    public MemoDto updteMemo(MemoDto mDto) {
        // 업데이트 대상 찾기
        Memo entity = memoRepository.findById(mDto.getMno()).get();
        // 변경
        entity.setMemoText(mDto.getMemoText());

        return entityToDto(memoRepository.save(entity));
    }

    public void deleteMemo(Long mno) {
        // 방법1
        // Memo entity = memoRepository.findById(mno).get();
        // memoRepository.delete(entity);

        // 방법2
        memoRepository.deleteById(mno);
    }

    public void insertMemo(MemoDto mDto) {
        // dto를 ==> entity로 바꾸는 코드 (dto바구니를 entity 바구니로 바꾸기)
        memoRepository.save(dtoToEntity(mDto));
    }

    private MemoDto entityToDto(Memo entity) {
        MemoDto mDto = MemoDto.builder()
                .mno(entity.getMno())
                .memoText(entity.getMemoText())
                .build();

        return mDto;
    }

    private Memo dtoToEntity(MemoDto mDto) {
        Memo entity = Memo.builder()
                .mno(mDto.getMno())
                .memoText(mDto.getMemoText())
                .build();

        return entity;
    }
}
