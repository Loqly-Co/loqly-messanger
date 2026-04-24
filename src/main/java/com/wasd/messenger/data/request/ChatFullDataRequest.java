package com.wasd.messenger.data.request;

import java.util.UUID;

public record ChatFullDataRequest(UUID user1, UUID user2) {}
