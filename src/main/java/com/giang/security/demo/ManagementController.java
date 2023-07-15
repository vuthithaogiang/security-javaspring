package com.giang.security.demo;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management")
public class ManagementController {

    @Operation(
            description = "Get endpoint for management",
            summary = "This is summary for management",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"
                    )
            }
    )

    @GetMapping
    public String get() {
        return "GET :: management controller";
    }

    @PostMapping
    public String  post() {
        return "POST :: management controller";
    }

    @PutMapping
    public String put() {
        return "PUT :: management controller";
    }

    @DeleteMapping
    public String delete( ) {
        return "DELETE :: management controller";
    }
}