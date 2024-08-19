package com.example.demo.board.board.presentation;

import com.example.demo.board.board.presentation.dto.GoogleApiResponse;
import com.example.demo.board.board.presentation.dto.BoardRequest;
import com.example.demo.config.util.SecurityUtil;
import com.example.demo.board.board.application.BoardServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// 유저 정보 불러오기 오류 수정했습니다
// 게시글 작성까지 잘 되는 걸로 확인 했어요!
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board/authed")
@Tag(name = "게시글 API (Authed)")
@PreAuthorize("isAuthenticated()")
public class AuthedBoardController {
    private final BoardServiceImpl boardServiceImpl;

    // 통일성을 주기위해 메서드 명을 save로 바꿨습니다!
    @PostMapping
    @Operation(summary = "게시글 작성")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "게시글 작성 성공"),
                    @ApiResponse(responseCode = "400", description = "게시글 작성 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
            }
    )
    public ResponseEntity<String> save(
            @Valid @RequestBody BoardRequest.DefaultBoard request,
            HttpServletRequest requestArr
    ){
        String ip = requestArr.getRemoteAddr();
        log.info("{}: 게시글 작성 호출", ip);

        Long memberId = SecurityUtil.getCurrentMemberId();

        boardServiceImpl.save(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 작성 성공");
    }

    @GetMapping("/search")
    @Operation(summary = "구글 api 를 이용한 장소 검색")
    public ResponseEntity<GoogleApiResponse> locationSearch(@RequestParam String textQuery,
                                                            HttpServletRequest requestArr){

        String ip = requestArr.getRemoteAddr();
        log.info("{}: 검색 호출", ip);

        return ResponseEntity.ok(boardServiceImpl.getSearchLocation(textQuery));
    }

    @PutMapping("/update/{boardId}")
    @Operation(summary = "게시글 수정")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "게시글 수정 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
            }
    )
    public ResponseEntity<String> boardUpdate(
            @Valid @RequestBody BoardRequest.BoardUpdateRequest request,
            @PathVariable Long boardId,
            HttpServletRequest requestArr
    ){
        String ip = requestArr.getRemoteAddr();
        log.info("{}: 게시글 수정 호출", ip);

        Long memberId = SecurityUtil.getCurrentMemberId();
        boardServiceImpl.updateById(boardId, memberId, request);
        return ResponseEntity.ok("게시글 수정 성공");
    }

    @DeleteMapping("/delete/{boardId}")
    @Operation(summary = "게시글 삭제")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "게시글 삭제 실패"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
            }
    )
    public ResponseEntity<String> boardDelete(@PathVariable("boardId") Long boardId,
                                              HttpServletRequest requestArr){
        String ip = requestArr.getRemoteAddr();
        log.info("{}: 게시글 삭제 호출", ip);

        Long memberId = SecurityUtil.getCurrentMemberId();
        boardServiceImpl.deleteById(boardId, memberId);
        return ResponseEntity.ok("게시글 삭제 성공");
    }
}
