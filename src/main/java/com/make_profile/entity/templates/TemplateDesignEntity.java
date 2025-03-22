package com.make_profile.entity.templates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "template_design")
public class TemplateDesignEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long usedTemplateId;

	@Column
	private Long fontId;

	@Column
	private Long colorId;

	@Column
	private boolean leftSide;

	@Column
	private boolean rightSide;

	@Column
	private boolean twoLayer;

	@Column
	private boolean singleLayer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsedTemplateId() {
		return usedTemplateId;
	}

	public void setUsedTemplateId(Long usedTemplateId) {
		this.usedTemplateId = usedTemplateId;
	}

	public Long getFontId() {
		return fontId;
	}

	public void setFontId(Long fontId) {
		this.fontId = fontId;
	}

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}

	public boolean isLeftSide() {
		return leftSide;
	}

	public void setLeftSide(boolean leftSide) {
		this.leftSide = leftSide;
	}

	public boolean isRightSide() {
		return rightSide;
	}

	public void setRightSide(boolean rightSide) {
		this.rightSide = rightSide;
	}

	public boolean isTwoLayer() {
		return twoLayer;
	}

	public void setTwoLayer(boolean twoLayer) {
		this.twoLayer = twoLayer;
	}

	public boolean isSingleLayer() {
		return singleLayer;
	}

	public void setSingleLayer(boolean singleLayer) {
		this.singleLayer = singleLayer;
	}

}
