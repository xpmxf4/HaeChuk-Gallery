package HailYoungHan.Board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// BoardApplication 에서 @EnableJpaAuditing 제외하기 위한 설정 클래스
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}
