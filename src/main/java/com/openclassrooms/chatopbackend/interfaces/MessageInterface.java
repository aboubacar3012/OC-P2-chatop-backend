package com.openclassrooms.chatopbackend.interfaces;

import java.util.Map;

public interface MessageInterface {
    public Map<String, String> create(com.openclassrooms.chatopbackend.model.Message message);
}
