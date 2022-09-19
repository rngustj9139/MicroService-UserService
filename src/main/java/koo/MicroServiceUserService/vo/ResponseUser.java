package koo.MicroServiceUserService.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // 아래 프로퍼티중 null 값이 들어가는건 그냥 응답으로 안 내려보내준다는 의미이다. ex - email 프로퍼티가 null이면 email프로퍼티 자체가 응답 데이터에 포함되지 않는다.
public class ResponseUser {

    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orders;
}
