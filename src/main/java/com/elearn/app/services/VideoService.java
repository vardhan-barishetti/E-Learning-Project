package com.elearn.app.services;

import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.dtos.VideoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VideoService {
    VideoDto createVideo(VideoDto videoDto);
    VideoDto updateVideo(String videoId, VideoDto videoDto);
    VideoDto getVideoById(String videoId);
    CustomPageResponse<VideoDto> getAllVideos(int pageNumber, int pageSize, String sortBy);
    void deleteVideo(String videoId);
    List<VideoDto> searchVideos(String keyword);
}
