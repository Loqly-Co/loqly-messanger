package com.wasd.messenger.keys;

import com.wasd.messenger.entity.PersonMain;
import com.wasd.messenger.entity.RefHeader;
import com.wasd.messenger.entity.Reference;
import com.wasd.messenger.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityKeys {

	PERSON_MAIN(PersonMain.ENTITY_NAME, "Основные данные по клиенту"),

	REFERENCE(Reference.ENTITY_NAME, "Справочник"),

	REF_HEADER(RefHeader.ENTITY_NAME, "Категория справочника"),

	USER(User.ENTITY_NAME, "Пользователь");

	private final String entityName;
	private final String description;
}