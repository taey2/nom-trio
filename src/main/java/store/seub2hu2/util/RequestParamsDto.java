package store.seub2hu2.util;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class RequestParamsDto {
    private int page = 1; // 기본값
    private int rows = 10; // 기본값
    private String sort = "date"; // 기본값
    private String opt;
    private String keyword = null;
    private String category;
    private String type;
    private int begin;
    private int end;
    private String value;
    private String day;
}
