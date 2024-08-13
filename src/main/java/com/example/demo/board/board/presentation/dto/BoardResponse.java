package com.example.demo.board.board.presentation.dto;

import com.example.demo.board.board.domain.Board;
import com.example.demo.board.boardscore.domain.BoardScore;
import com.example.demo.board.boardscore.presentation.dto.BoardScoreResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {

    private Long id;
    private String locationName;
    private String formattedAddress;
    private String latitude;  // 위도
    private String longitude;  // 경도
    private String title;
    private String body;
    private Integer view;
    private Integer satisfaction;
    private Integer likeCount;
    private String memberNickname;

    public record BoardSaveResponse(
            String locationName,
            String formattedAddress,
            String latitude,  // 위도
            String longitude,  // 경도
            String title,
            String body,
            Integer view,
            Integer satisfaction,
            String memberNickname
    ) {
        public BoardSaveResponse(Board board) {
            this(
                    board.getLocationName(),
                    board.getFormattedAddress(),
                    board.getLatitude(),
                    board.getLongitude(),
                    board.getTitle(),
                    board.getBody(),
                    board.getView(),
                    board.getSatisfaction(),
                    board.getMemberNickname()
            );
        }
    }

    public record BoardUpdateResponse(
            String locationName,
            String formattedAddress,
            String latitude,  // 위도
            String longitude,  // 경도
            String title,
            String body,
            Integer view,
            Integer satisfaction,
            String memberNickname
    ) {
        public BoardUpdateResponse(Board board) {
            this(
                    board.getLocationName(),
                    board.getFormattedAddress(),
                    board.getLatitude(),
                    board.getLongitude(),
                    board.getTitle(),
                    board.getBody(),
                    board.getView(),
                    board.getSatisfaction(),
                    board.getMemberNickname()
            );
        }
    }

    public record BoardListResponse(
            Long id,
            String locationName,
            String formattedAddress,
            String latitude,  // 위도
            String longitude,  // 경도
            String title,
            Integer view,
            Integer satisfaction,
            String memberNickname
    ) {
        public BoardListResponse(Board board) {
            this(
                    board.getId(),
                    board.getLocationName(),
                    board.getFormattedAddress(),
                    board.getLatitude(),
                    board.getLongitude(),
                    board.getTitle(),
                    board.getView(),
                    board.getSatisfaction(),
                    board.getMemberNickname()
            );
        }
    }

    public record BoardReadResponse(
            String locationName,
            String formattedAddress,
            String latitude,  // 위도
            String longitude,  // 경도
            String title,
            String Body,
            Integer view,
            Integer satisfaction,
            String memberNickname,
            BoardScoreResponse.BoardScoreByBoardId boardScore
    ) {
        public BoardReadResponse(Board board, BoardScoreResponse.BoardScoreByBoardId boardScore) {
            this(
                    board.getLocationName(),
                    board.getFormattedAddress(),
                    board.getLatitude(),
                    board.getLongitude(),
                    board.getTitle(),
                    board.getBody(),
                    board.getView(),
                    board.getSatisfaction(),
                    board.getMemberNickname(),
                    boardScore
            );
        }
    }

}
