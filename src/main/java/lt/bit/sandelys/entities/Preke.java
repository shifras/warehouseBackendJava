package lt.bit.sandelys.entities;

import java.awt.desktop.PrintFilesEvent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "prekes")
public class Preke {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true)
	private String name;
	
	@Column
	private String description;
	
	@Column
	private Double price;
	
	@Column
	private Integer ammount;
	
	@Column
	private String image;

	public Preke() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Preke(Integer id, String name, String description, Double price, Integer ammount, String image) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.ammount = ammount;
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAmmount() {
		return ammount;
	}

	public void setAmmount(Integer ammount) {
		this.ammount = ammount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Preke [id=" + id + ", name=" + name + ", price=" + price + ", ammount=" + ammount + ", image=" + image
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		Preke p=(Preke) obj;
		return this.name.equals(p.name) && description.equals(p.description) && price.equals(p.price) && ammount.equals(p.ammount) && image.equals(p.image);
	}
	
	
	
	
	
}
