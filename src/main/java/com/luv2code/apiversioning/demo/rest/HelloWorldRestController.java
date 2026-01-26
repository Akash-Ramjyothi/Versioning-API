package com.luv2code.apiversioning.demo.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = "/api/{version}/hello",
        produces = MediaType.TEXT_PLAIN_VALUE
)
@Tag(name = "Hello API", description = "Versioned hello-world endpoints")
public class HelloWorldRestController {

    @GetMapping
    @Operation(summary = "Hello World (path versioned)")
    public String hello(
            @PathVariable
            @Pattern(regexp = "v1|v2|v3", message = "Supported versions: v1, v2, v3")
            String version
    ) {
        return switch (version) {
            case "v1" -> "Hello World from API v1";
            case "v2" -> "Howdy World from API v2";
            case "v3" -> "Hey Hey World from API v3";
            default -> throw new IllegalArgumentException("Unsupported API version");
        };
    }

    // --- Optional alternatives for clients ---

    @GetMapping(path = "/header", headers = "X-API-Version=1")
    public String helloHeaderV1() {
        return "Hello World from API v1 (header)";
    }

    @GetMapping(path = "/header", headers = "X-API-Version=2")
    public String helloHeaderV2() {
        return "Howdy World from API v2 (header)";
    }

    @GetMapping(path = "/param", params = "version=3")
    public String helloParamV3() {
        return "Hey Hey World from API v3 (query param)";
    }
}
