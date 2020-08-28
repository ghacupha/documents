/**
 * A document to be shared can either be transaction-document or a formal document.
 * This limitation helps the service determine whether to display a datatable of one or the other
 */
export const enum DocumentSharingType {
  TRANSACTION = 'TRANSACTION',
  FORMAL = 'FORMAL'
}

/**
 * This model represents the relationship between and email address, and the
 * username with which the system will address them. It will also record the
 * name by which the recipient knows the correspondent.
 *
 * With this info the mail will look like so:
 *
 *     Dear recipientUserName,
 *
 *     correspondentUsername has shared with you the following attachments
 *         - filename
 *         - filename
 *         - ...
 *
 *     Regards
 *     Documents Team
 *
 * This is a bit of over-engineering but, we wouldn't want to misrepresent where
 * the document came from, since the user's address may also be used as a server
 * and depending on who the recipient is, it might look like a fraudulent email.
 * Having this down helps us come up with a list of multiples of these guys on the
 * sharing form
 */
export interface IEmailRecipient {
  /**
   * The name which the user would want the recipient to use for them
   */
  correspondentUsername?: string;

  /**
   * The name of the recipient
   */
  recipientUsername?: string;

  /**
   * This is the address of the email
   */
  recipientEmailAddress?: string;
}

/**
 * These are details specific to the email recipient that the email recipient
 * will see
 */
export class EmailRecipient implements IEmailRecipient {
  constructor(public correspondentUsername?: string, public recipientUsername?: string, public recipientEmailAddress?: string) {}

  toString(): string {
    return `correspondent: ${this.correspondentUsername} recipient: ${this.recipientUsername} email: ${this.recipientEmailAddress}`;
  }
}

/**
 * This objects enables the system to share multiple documents albeit of one type
 * with multiple recipients at the same time.
 */
export interface ISharingSpecificationData {
  /**
   * Having an id means we can have multiple shares send to the poor server
   * and why not?
   * This means we can specify multiple things and requests and then have them executed at the
   * same time
   */
  id?: number;

  /**
   * This is a conventional way in which the developer likes to write his email and we hope you like
   * it wo because you have no other choice. Coupled with the sharingSubtitle and the brief description
   * the email headline will look like so :
   *
   * SHARING_TITLE : SharingSubTitle
   *
   * Dear recipientUsername,
   *
   * correspondent username has shared with you the following files...
   * briefDescription
   *
   * Regards
   * Documents Team
   */
  sharingTitle?: string;

  /**
   * See comments on sharingTitle
   */
  sharingSubTitle?: string;

  /**
   * See comments on sharingTitle
   */
  briefDescription?: string;

  /**
   * This enumeration specifies whether you are sharing a formal document or
   * a transaction document
   */
  documentSharingType?: DocumentSharingType;

  /**
   * This is an array of email recipients each of which will recieve an email with the
   * above title, sub-title, file attachments and brief description the number of times
   * it will take to share various partions of the same email if the file attachments are too
   * big
   */
  recipients?: IEmailRecipient[];

  /**
   * Depending on the recipient's email server this is the maximum size of attachments the server
   * can process at a given time in MBs (MegaBytes)
   */
  maximumFileSize?: number;
}

export class SharingSpecificationData implements ISharingSpecificationData {
  constructor(
    public id?: number,
    public sharingTitle?: string,
    public sharingSubTitle?: string,
    public briefDescription?: string,
    public documentSharingType?: DocumentSharingType,
    public recipients?: IEmailRecipient[],
    public maximumFileSize?: number
  ) {}

  toString(): string {
    const recipientsArrayString = '';

    this.recipients?.forEach(recipient => {
      recipientsArrayString.concat(recipient.toString() + ',');
    });

    return `Title: ${this.sharingTitle} SubTitle: ${this.sharingSubTitle} DocType: ${this.documentSharingType} shared with ${recipientsArrayString}`;
  }
}
