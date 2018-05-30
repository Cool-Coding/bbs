package cn.yang.web.formbean;


@SuppressWarnings("serial")
public class RoleForm extends BaseForm {
	private Integer id;
	private String name; // 名称
	private String description;// 描述
	private Integer[] systemPrivilegeIdList = {};

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

	public Integer[] getSystemPrivilegeIdList() {
		return systemPrivilegeIdList;
	}

	public void setSystemPrivilegeIdList(Integer[] systemPrivilegeIdList) {
		this.systemPrivilegeIdList = systemPrivilegeIdList;
	}

}
