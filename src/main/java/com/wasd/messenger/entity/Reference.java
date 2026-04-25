package com.wasd.messenger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Reference.ENTITY_NAME)
@Entity
public class Reference extends AuditableEntity {

    public static final String ENTITY_NAME = "reference";

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "code")
    private Integer code;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private RefHeader refHeader;

    @Column(name = "comment")
    private String comment;

    public boolean is(Integer code) {
        if (Objects.isNull(code)) {
            return false;
        }
        return this.code.equals(code);
    }

    public boolean in(Integer... codes) {
        return Arrays.stream(codes)
                .anyMatch(this::is);
    }

    public boolean in(Collection<Integer> codes) {
        return codes.stream()
                .anyMatch(this::is);
    }

    public boolean isType(Integer refHeaderCode) {
        return this.refHeader.is(refHeaderCode);
    }

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof Reference reference)) return false;
		return Objects.equals(getCode(), reference.getCode()) && isType(reference.getRefHeader().getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCode(), getRefHeader().getId());
	}
}