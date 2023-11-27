package HailYoungHan.Board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

// BoardApplication 에서 @EnableJpaAuditing 제외하기 위한 설정 클래스
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.of("CoRaveler");
    }
}
