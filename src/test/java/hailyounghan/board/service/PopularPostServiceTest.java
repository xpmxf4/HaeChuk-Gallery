package hailyounghan.board.service;

import com.querydsl.core.Tuple;
import hailyounghan.board.dto.popularPost.query.PopularPostDTO;
import hailyounghan.board.dto.popularPost.response.PopularPostResponseDTO;
import hailyounghan.board.repository.popularPost.PopularPostRepository;
import hailyounghan.board.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import hailyounghan.board.entity.PopularPost;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PopularPostServiceTest {

    @Mock
    private PopularPostRepository popularPostRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PopularPostService popularPostService;

    @Test
    @DisplayName("updatePopularPosts 메서드가 매시 정각에 실행되어야 한다")
    public void shouldTrigger_updatePopularPosts_atEveryHour() throws ParseException {
        // Given - 상황 설정
        // 매시 정각을 의미하는 cron 표현식 설정
        String cronExpression = "0 0 * * * *";
        // Cron 트리거 생성: 주어진 cron 표현식으로 다음 실행 시간을 계산할 객체
        CronTrigger trigger = new CronTrigger(cronExpression);
        // 테스트 시작 시간을 설정함
        Date startTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2023/12/20 00:00:00");
        // 스케줄링 컨텍스트 초기화
        SimpleTriggerContext context = new SimpleTriggerContext();
        context.update(startTime, startTime, startTime);
        // 예상되는 실행 시간 목록
        List<String> expectedTimes = Arrays.asList(
                "2023/12/20 01:00:00",
                "2023/12/20 02:00:00",
                "2023/12/20 03:00:00");

        // When - 동작 수행
        // 각 예상 시간에 대해 cron 표현식에 따라 계산된 다음 실행 시간을 검증
        for (String expectedTime : expectedTimes) {
            Date nextExecutionTime = trigger.nextExecutionTime(context);

            // Then - 결과 검증
            // 계산된 실행 시간이 예상 시간과 일치하는지 확인
            String actualTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(nextExecutionTime);
            assertThat(actualTime).isEqualTo(expectedTime);
            // 다음 예상 시간 계산을 위해 컨텍스트 업데이트
            context.update(nextExecutionTime, nextExecutionTime, nextExecutionTime);
        }
    }

    @Test
    @DisplayName("인기 게시글을 가져오기")
    void shouldFetchPopularPosts() {
        // given
        List<PopularPostDTO> popularPostDTOs = Arrays.asList(
                new PopularPostDTO(1L, "인기 게시글 제목 1", "작성자1"),
                new PopularPostDTO(2L, "인기 게시글 제목 2", "작성자2")
        );
        given(popularPostRepository.findAllDTOs()).willReturn(popularPostDTOs);

        // when
        PopularPostResponseDTO result = popularPostService.getPopularPosts();

        // then
        then(popularPostRepository).should().findAllDTOs();
        assertThat(result.getPopularPosts()).hasSize(popularPostDTOs.size());
    }

    //    @Test
//    @DisplayName("인기 게시글을 업데이트")
//    void shouldUpdatePopularPosts() {
//        // given
//        List<Tuple> top10Posts = // ... 적절한 튜플 데이터를 생성
//                given(postRepository.findTop10Posts()).willReturn(top10Posts);
//        // 필요한 경우 다른 mock 설정을 추가
//
//        // when
//        popularPostService.updatePopularPosts();
//
//        // then
//        then(popularPostRepository).should().deleteAllInBatch();
//        then(popularPostRepository).should().saveAll(anyList());
//    }
//
    @Test
    @DisplayName("인기 게시글 업데이트 작업을 수행")
    void shouldUpdatePopularPosts() {
        // given
        List<Tuple> top10Posts = Arrays.asList(mock(Tuple.class), mock(Tuple.class));
        given(postRepository.findTop10Posts()).willReturn(top10Posts);

        // when
        popularPostService.updatePopularPosts();

        // then
        then(popularPostRepository).should().deleteAllInBatch(); // 기존 데이터가 삭제되었는지 확인
        then(popularPostRepository).should(times(1)).saveAll(anyList()); // 새로운 데이터가 삽입되었는지 확인
    }
}