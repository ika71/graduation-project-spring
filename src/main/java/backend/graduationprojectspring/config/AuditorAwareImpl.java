package backend.graduationprojectspring.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String memberId = "임시";

        return Optional.of(memberId);
    }
}
