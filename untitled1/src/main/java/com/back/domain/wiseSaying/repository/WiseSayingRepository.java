package com.back.domain.wiseSaying.repository;

//하는 일 : 명언 불러오기, 명언 저장하기, 마지막 id 번호 불러오기, 마지막 id 늘리기, 마지막 id 저장하기, 빌드 저장하기,
// 파일 삭제하기

import com.back.domain.wiseSaying.entity.WiseSaying;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WiseSayingRepository {

    String url = "jdbc:mysql://localhost:3306/wisesaying";
    String user = "root";
    String password = "1234";

    Connection conn;


    public WiseSayingRepository() {
        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean remove(int id) {
        try {
            String sql = "DELETE FROM `wise_saying` WHERE `id` = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            ps.close();
            return rowsAffected > 0; // 삭제된 행이 있으면 true, 없으면 false
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<WiseSaying> parsingWiseSayingList(ResultSet rs) throws SQLException {

        List<WiseSaying> list = new ArrayList<>();
        while(rs.next()) {

            int id = rs.getInt("id");
            String content = rs.getString("content");
            String author= rs.getString("author");

            WiseSaying ws = new WiseSaying(id,content,author);

            list.add(ws);
        }


        return list;
    }


    public void build() {
        try {
            File dir = new File("./db/wiseSaying");
            if (!dir.exists()) dir.mkdirs(); // 파일이 없을 경우 생성
            StringBuilder outputString = new StringBuilder("[");
            OutputStream output = new FileOutputStream("./db/wiseSaying/data.json");
            boolean first = true;

            List<WiseSaying> wiseSayingList = getWiseSayingList();
            for (WiseSaying ws : wiseSayingList) {
                if (!first) {
                    outputString.append(",");
                }
                first = false;
                int id = ws.getId();
                String content = ws.getContent();
                String author = ws.getAuthor();

                outputString.append("{\n\t\"id\" : ").append(id).append(",\n\t\"content\":\"").append(content).append("\",\n\t\"author\":\"").append(author).append("\"\n}");
            }
            outputString.append(']');
            //DEBUG
            //System.out.println("빌드 String: " + outputString);
            output.write(outputString.toString().getBytes());
            output.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public int getLastIdFromFile() {
        try {
//            File dir = new File("./db/wiseSaying");
//            if (!dir.exists()) dir.mkdirs();
//            BufferedReader reader = new BufferedReader(new FileReader("./db/wiseSaying/lastId.txt"));
//            String line = reader.readLine();
//            reader.close();
//
////            DEBUG
////            System.out.println("가져온 마지막 ID " + line);
//
//            return Integer.parseInt(line);
            String sql  = "SELECT MAX(id) FROM `wise_saying`";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            rs.close();
            ps.close();
            return 0;

        } catch (Exception e) {
            e.getStackTrace();

        }
        return 0;
    }
/*
    public void setLastIdForFile(int id) {
        try {
            File dir = new File("./db/wiseSaying");
            if (!dir.exists()) dir.mkdirs();
            OutputStream output = new FileOutputStream("./db/wiseSaying/lastId.txt");

            //DEBUG
//            System.out.println("저장한 마지막 번호 " + number);

            output.write(String.valueOf(id).getBytes());
            output.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
*/

    public List<WiseSaying> getWiseSayingList() {
        try {
            List<WiseSaying> wiseSayingList = new ArrayList<WiseSaying>();
//            int lastId = getLastIdFromFile();
//            for (int i = 1; i <= lastId; i++) {
//                String fileName = "./db/wiseSaying/" + i + ".json";
//                File file = new File(fileName);
//                if (file.exists()) {
//                    //DEBUG
//                    //System.out.println("파일을 찾음, 파일주소 :" + fileName );
//                    wiseSayingList.add(readJsonFile(fileName));
//                }
//            }
            String sql  = "SELECT * FROM wise_saying";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            try{
                wiseSayingList= parsingWiseSayingList(rs);
            }catch(Exception e){
                e.printStackTrace();
            }
            rs.close();
            ps.close();
            return wiseSayingList;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
//Not used
/*    private WiseSaying readJsonFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }

            String json = sb.toString();

            //DEBUG
            //System.out.println("읽은 json 파일" + json);

            return parseWiseSayings(json);

        } catch (IOException e) {
            System.out.println("에러 발생");
            e.printStackTrace();
            return null;
        }
    }

    private WiseSaying parseWiseSayings(String json) {
        WiseSaying w = new WiseSaying();

        // id
        String idStr = json.split("\"id\"\\s*:\\s*")[1].split(",")[0].trim();
        w.setId(Integer.parseInt(idStr));

        // content
        String contentStr = json.split("\"content\"\\s*:\\s*\"")[1].split("\"")[0];
        w.setContent(contentStr);

        // author
        String authorStr = json.split("\"author\"\\s*:\\s*\"")[1].split("\"")[0];
        w.setAuthor(authorStr);

        //DEBUG
        //System.out.println(w.getNumber() + " " + w.getContent() + " " + w.getAuthor() + " ");
        return w;
    }
*/
    public WiseSaying read(int id) {
//        try {
//            String fileName = "./db/wiseSaying/" + id + ".json";
//            File file = new File(fileName);
//            if (file.exists()) {
//                //DEBUG
//                //System.out.println("파일을 찾음, 파일주소 :" + fileName );
//                return readJsonFile(fileName);
//            }
//            return null;
//        } catch (Exception e) {
//            e.getStackTrace();
//            return null;
//        }
        try {
            String sql = "SELECT * FROM wise_saying WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                WiseSaying ws = new WiseSaying(
                    rs.getInt("id"),
                    rs.getString("content"),
                    rs.getString("author")
                );
                rs.close();
                ps.close();
                return ws;
            }
            rs.close();
            ps.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void write(WiseSaying ws) { //수정
        try {
//            File dir = new File("./db/wiseSaying");
//            if (!dir.exists()) dir.mkdirs(); // 파일이 없을 경우 생성
//            OutputStream output = new FileOutputStream("./db/wiseSaying/" + ws.getId() + ".json");
//            String outputString = "{\n\t\"id\" : " + ws.getId() + ",\n\t\"content\":\"" + ws.getContent() + "\",\n\t\"author\":\"" + ws.getAuthor() + "\"\n}";
//
//            //DEBUG
//            //System.out.println("저장하는 String: " + outputString);
//
//            output.write(outputString.getBytes());
//            output.close();
            // 기존 레코드가 있는지 확인
            WiseSaying existing = read(ws.getId());
            
            if (existing != null) {
                // 기존 레코드가 있으면 UPDATE
                String sql = "UPDATE wise_saying SET content = ?, author = ? WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ws.getContent());
                ps.setString(2, ws.getAuthor());
                ps.setInt(3, ws.getId());
                ps.executeUpdate();
                ps.close();
            } else {
                // 기존 레코드가 없으면 INSERT
                String sql = "INSERT INTO wise_saying VALUES(?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, ws.getId());
                ps.setString(2, ws.getContent());
                ps.setString(3, ws.getAuthor());
                ps.executeUpdate();
                ps.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

