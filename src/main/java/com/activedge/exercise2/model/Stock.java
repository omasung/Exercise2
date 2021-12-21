package com.activedge.exercise2.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "stock")

public class Stock implements Serializable {

	private static final long serialVersionUID = 1L;

    @GenericGenerator(
      name = "stock-sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "stock_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )

	@Id
    @GeneratedValue(generator = "stock-sequence-generator")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id; 
    
    @Column(name = "name")
    private String name; 
    
    @Column(name = "current_price")
    private Double current_price; 
    
    @Column(name = "create_date")
    private LocalDateTime create_date; 
    
    @Column(name = "last_update")
    private LocalDateTime last_update; 
    
	public Stock() {
		
    }

    public Stock(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(Double current_price) {
		this.current_price = current_price;
	}

	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}

	public LocalDateTime getLast_update() {
		return last_update;
	}

	public void setLast_update(LocalDateTime last_update) {
		this.last_update = last_update;
	}

}
