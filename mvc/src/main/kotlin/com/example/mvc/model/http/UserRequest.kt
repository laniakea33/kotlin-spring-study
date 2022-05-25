package com.example.mvc.model.http

import com.example.mvc.annotation.StringFormatDateTime
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.*

//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class UserRequest(
    @field:NotEmpty //  Bean
    @field:Size(min = 2, max = 5)
    var name: String? = null,
    @field:PositiveOrZero
    var age: Int? = null,
    @field:NotBlank
    var address: String? = null,
    @field:Email
    var email: String? = null,
    //  요청할 때는 보통 snake case로 'phone_number' 이렇게 보냄.
    //  @JsonProperty 애너테이션으로 해당 프로퍼티의 json에서의 이름을 정해 줌
    //  모든 프로퍼티를 동일하게 snake case 또는 다른 규칙으로 설정하기 위해서는 클래스에 @JsonNaming애너테이션을 설정해줌
    //  예를 들어서 @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    //    @JsonProperty("phone_number")
    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
    var phoneNumber: String? = null,

    @field:StringFormatDateTime(pattern = "yyyy-MM-dd HH:mm:ss", message = "패턴이 올바르지 않습니다.")
    var createdAt: String? = null,  //  format : yyyy-MM-dd HH:mm:ss
) {

    //  메소드를 통한 Validation
//    @AssertTrue(message = "생성일자의 패턴은 yyyy-MM-dd HH:mm:ss여야 합니다.") //  메소드라 @field: 안붙여도 된다.
//    private fun isValidCreateAt(): Boolean {
//        return try {
//            LocalDateTime.parse(this.createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }
}
