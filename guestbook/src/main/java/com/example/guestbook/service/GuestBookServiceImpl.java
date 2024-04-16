package com.example.guestbook.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.example.guestbook.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;

    // 페이지 나누기 전
    // @Override
    // public List<GuestBookDto> getList() {
    // List<GuestBook> entities = guestBookRepository.findAll();
    // Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
    // return entities.stream().map(fn).collect(Collectors.toList());
    // }

    @Override
    public PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());
        // PageingAndSortingRepository.findAll(Pageable pageable)
        // Page<GuestBook> result = guestBookRepository.findAll(pageable);

        BooleanBuilder builder = getSearch(requestDto);
        // querydsl.QuerydslPredicateExecutor.findAll()
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<GuestBookDto, GuestBook>(result, fn);
    }

    @Override
    public GuestBookDto getRow(Long gno) {
        GuestBook guestBook = guestBookRepository.findById(gno).get();
        return entityToDto(guestBook);

        // 위 코드를 한줄로 변경
        // return entityToDto(guestBookRepository.findById(gno).get());
    }

    @Override
    public Long bookUpdate(GuestBookDto updateDto) {
        GuestBook entity = guestBookRepository.findById(updateDto.getGno()).get();
        entity.setTitle(updateDto.getTitle());
        entity.setContent(updateDto.getContent());
        GuestBook updateBook = guestBookRepository.save(entity);

        return updateBook.getGno();
    }

    @Override
    public void bookDelete(Long gno) {
        GuestBook entity = guestBookRepository.findById(gno).get();
        guestBookRepository.delete(entity);
    }

    @Override
    public Long insert(GuestBookDto dto) {
        GuestBook guestBook = guestBookRepository.save(dtoToEntity(dto));
        return guestBook.getGno();
    }

    // 검색 기능
    // Book 프로젝트에서는 BookRepository 에 작성함 makePredicate
    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        BooleanBuilder builder = new BooleanBuilder();

        // Q 클래스 QGuestBook 사용
        QGuestBook guestBook = QGuestBook.guestBook;

        // type, keyword 가져오기
        String type = requestDto.getType();
        String keyword = requestDto.getKeyword();

        // WHERE gno>0 AND title LIKE '%Title%' OR content LIKE '%content%'
        // gno > 0 (gt : graterthan)
        // gno > 0 쓰는 이유 : 성능이 좋아짐(빨라짐)
        builder.and(guestBook.gno.gt(0L));

        if (type == null || type.trim().length() == 0) {
            return builder;
        }

        // 검색 타입이 있는 경우
        // tc, tcw 때문에 contains 사용
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            // OR content LIKE '%content%'
            conditionBuilder.or(guestBook.title.contains(keyword));
        }
        if (type.contains("c")) {
            // OR content LIKE '%content%'
            conditionBuilder.or(guestBook.content.contains(keyword));
        }
        if (type.contains("w")) {
            // OR content LIKE '%content%'
            conditionBuilder.or(guestBook.writer.contains(keyword));
        }
        builder.and(conditionBuilder);

        return builder;
    }

}
