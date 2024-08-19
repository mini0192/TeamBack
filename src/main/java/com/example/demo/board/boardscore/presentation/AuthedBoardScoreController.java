package com.example.demo.board.boardscore.presentation;

import com.example.demo.board.boardscore.application.BoardScoreService;
import com.example.demo.board.boardscore.presentation.dto.BoardScoreRequest;
import com.example.demo.config.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "별점 정보 API (Authed)")
@PreAuthorize("isAuthenticated()")
@RequestMapping("/board/score/authed")
public class AuthedBoardScoreController {

    private final BoardScoreService boardScoreService;

    @PostMapping("/{boardId}")
    @Operation(summary = "별점 등록", description = "게시글에 별점을 기록합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "별점 등록 완료"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "409", description = "이미 별점 등록을 했음"),
            }
    )
    public ResponseEntity<String> save(@PathVariable("boardId") Long boardId,
                                       @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
                                       @Valid @RequestBody BoardScoreRequest.SaveScore takenDto,
                                       HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("{}: 별점 등록 호출", ip);

        Long memberId = SecurityUtil.getCurrentMemberId();
        boardScoreService.save(takenDto, boardId, memberId);
        return ResponseEntity.ok("별점 등록에 성공하였습니다");
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary = "별점 등록", description = "게시글에 별점을 기록합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "별점 등록 완료"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
                    @ApiResponse(responseCode = "409", description = "이미 별점 등록을 했음"),
            }
    )
    public ResponseEntity<String> delete(@PathVariable("boardId") Long boardId,
                                         HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("{}: 별점 삭제 호출", ip);

        Long memberId = SecurityUtil.getCurrentMemberId();
        boardScoreService.delete(boardId, memberId);
        return ResponseEntity.ok("별점 등록에 성공하였습니다");
    }
}
