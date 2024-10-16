package com.example.springjpa.schedule.api.dto.request;

import java.util.Set;

public record AssignUsersRequest(
        Set<Long> userIds
) {
}
