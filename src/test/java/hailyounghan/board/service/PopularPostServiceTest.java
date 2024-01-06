package hailyounghan.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PopularPostServiceTest {

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
            assertThat(actualTime, is(expectedTime));
            // 다음 예상 시간 계산을 위해 컨텍스트 업데이트
            context.update(nextExecutionTime, nextExecutionTime, nextExecutionTime);
        }
    }
}