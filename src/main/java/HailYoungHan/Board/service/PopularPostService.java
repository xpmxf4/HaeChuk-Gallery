package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.popularPost.query.PopularPostDTO;
import HailYoungHan.Board.dto.popularPost.response.PopularPostResponseDTO;
import HailYoungHan.Board.repository.popularPost.PopularPostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularPostService {

    private final PopularPostRepository repository;

    public PopularPostResponseDTO getPopularPosts() {
        List<PopularPostDTO> allDTOs = repository.findAllDTOs();
        return new PopularPostResponseDTO(allDTOs);
    }
}
