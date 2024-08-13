package com.example.demo.member.member.presentation;

import com.example.demo.config.util.SecurityUtil;
import com.example.demo.member.member.application.MemberService;
import com.example.demo.member.member.presentation.dto.MemberRequest;
import com.example.demo.member.member.presentation.dto.MemberResponse;
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
@RequiredArgsConstructor
@RestController
@RequestMapping("/member/authed")
@Tag(name = "유저 API (Authed)")
@PreAuthorize("isAuthenticated()")
public class AuthedMemberController {

    private final MemberService memberService;
    @GetMapping
    @Operation(summary = "회원 정보 확인")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
                    @ApiResponse(responseCode = "400", description = "유저를 찾지 못함"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
            }
    )
    public ResponseEntity<MemberResponse.FindMember> findById(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("{}: 회원정보 호출", ip);

        Long userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(memberService.findById(userId));
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
                    @ApiResponse(responseCode = "400", description = "유저를 찾지 못함"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
            }
    )
    public ResponseEntity<String> patchPassword(@Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
                                                @Valid @RequestBody MemberRequest.patchPassword takenDto,
                                                HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("{}: 비밀번호 변경 호출", ip);

        Long userId = SecurityUtil.getCurrentUserId();

        memberService.patchPasswordByUsername(userId, takenDto.password());
        return ResponseEntity.ok("비밀번호 변경에 성공하였습니다");
    }

    @PatchMapping("/nickname")
    @Operation(summary = "닉네임 변경")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "닉네임 완료"),
                    @ApiResponse(responseCode = "400", description = "유저를 찾지 못함"),
                    @ApiResponse(responseCode = "403", description = "권한 없음"),
            }
    )
    public ResponseEntity<String> patchNickname(@Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
                                                @Valid @RequestBody MemberRequest.patchNickname takenDto,
                                                HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("{}: 닉네임 변경 호출", ip);

        Long userId = SecurityUtil.getCurrentUserId();

        memberService.patchNicknameById(userId, takenDto.nickname());
        return ResponseEntity.ok("닉네임 변경에 성공하였습니다");
    }
}
