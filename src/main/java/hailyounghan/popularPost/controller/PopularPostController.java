package hailyounghan.popularPost.controller;

import hailyounghan.popularPost.dto.response.PopularPostResponseDTO;
import hailyounghan.popularPost.service.PopularPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popularPost")
public class PopularPostController {

    private final PopularPostService service;

    @GetMapping
    public ResponseEntity<PopularPostResponseDTO> getPopularPosts() {
        PopularPostResponseDTO result = service.getPopularPosts();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> updatePopularPosts() {
        service.updatePopularPosts();
        return ResponseEntity.ok("Popular Post Updated");
    }
}
