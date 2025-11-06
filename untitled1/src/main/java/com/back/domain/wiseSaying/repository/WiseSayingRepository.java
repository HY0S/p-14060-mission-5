package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@Repository
public interface WiseSayingRepository extends JpaRepository<WiseSaying, Long> {
    
    // Author로 검색
    List<WiseSaying> findByAuthorContaining(String keyword);
    
    // Content로 검색
    List<WiseSaying> findByContentContaining(String keyword);
    
    // 전체 조회 (최신순)
    @Query("SELECT w FROM WiseSaying w ORDER BY w.id DESC")
    List<WiseSaying> findAllOrderByIdDesc();

    @Query("SELECT w FROM WiseSaying w ORDER BY w.id")
    List<WiseSaying> findAllOrderById();

    default void build(){
        try {
            File dir = new File("./db/wiseSaying");
            if (!dir.exists()) dir.mkdirs(); // 파일이 없을 경우 생성
            StringBuilder outputString = new StringBuilder("[");
            OutputStream output = new FileOutputStream("./db/wiseSaying/data.json");
            boolean first = true;

            List<WiseSaying> wiseSayingList = findAllOrderById();
            for (WiseSaying ws : wiseSayingList) {
                if (!first) {
                    outputString.append(",");
                }
                first = false;
                long id = ws.getId();
                String content = ws.getContent();
                String author = ws.getAuthor();

                outputString.append("{\n\t\"id\" : ").append(id).append(",\n\t\"content\":\"").append(content).append("\",\n\t\"author\":\"").append(author).append("\"\n}");
            }
            outputString.append(']');
            //DEBUG
            System.out.println("빌드 String: " + outputString);
            output.write(outputString.toString().getBytes());
            output.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
