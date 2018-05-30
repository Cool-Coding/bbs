package cn.yang.constant;

public class ControlCenterConstants {
	
  private ControlCenterConstants(){}
  
  /**页面所显示的记录数*/
  public static final String VIEW_PAGE_RECORDS="viewPageRecords";
  /**每个页面页码数*/
  public static final String VIEW_PAGE_COUNT="viewPageCount";
  /**禁用父版块时是否禁用所有子版块标志(默认为true)*/
  public static final String DISABLE_ALLCHILDREN_FORUM="disableAllChildren_forum";
  /**启用父版块时是否启用所有子版块标志(默认为true)*/
  public static final String ENABLE_ALLCHILDREN_FORUM="enableAllChildren_forum";
  /**简单版块列表的列数*/
  public static final String VIEW_SIMPLEFORUM_PAGE_RECORDS_COLS="viewPageRecordsCols_simpleForum";
  /**附件允许上传的大小*/
  public static final String ATTACHMENT_SIZE="attachment_size";
  /**删除帖子时是否也删除相应的附件*/
  public static final String THEME_DELETE_FOR_ATTACHMENT_DELETE="theme_delete_for_attachment_delete";
  /**用户上传附件之后，删除时是否进行物理删除（默认为false）*/
  public static final String ATTACHMENT_DELETE_FOR_USER="attachment_delete_for_user";
  /**允许用户上传附件的个数 */
  public static final String ATTACHMENT_COUNT="attachment_count";
  /**显示回帖时是否将不可见的回帖显示出来(默认为true)*/
  public static final String SHOW_INVISIBLE_REPLY="show_invisible_reply";
  /**显示回帖列表时，每页的回帖数*/
  public static final String REPLYCOUNT_PERPAGE="replyCount_perPage";
  
  //front constants
  /**前台页面所显示的记录行数*/
  public static final String FRONT_VIEW_PAGE_RECORDS="front_viewPageRecords";
  
  /**前台每个页面页码数*/
  public static final String FRONT_VIEW_PAGE_COUNT="front_viewPageCount";
  
  /**显示回帖列表时，每页的回帖数*/
  public static final String FRONT_REPLYCOUNT_PERPAGE="front_replyCount_perPage";
  
  /**会员每发表一个帖子增加多少分(默认为20分)*/
  public static final String FRONT_SCORE_PER_THEME="front_score_per_theme";
  
  /**会员每发表一个回复增加多少分(默认为5分)*/
  public static final String FRONT_SCORE_PER_REPLY="front_score_per_reply";
  //=============================================================
	/** 会员头像的最大宽度 */
	public static final String AVATAR_MAX_WIDTH = "avatar_max_width";

	/** 会员头像的最大高度 */
	public static final String AVATAR_MAX_HEIGHT = "avatar_max_height";

	/**用户自动登陆的天数*/
	public static final String USER_AUTO_LOGIN_DAYS="auto_login_days";
}
