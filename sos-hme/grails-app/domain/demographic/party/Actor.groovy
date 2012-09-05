/**
 * 
 */
package demographic.party

import demographic.role.Role

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 * Esta clase modela una entidad que puede tener varios roles.
 */
class Actor extends Party {

    static hasMany = [roles: Role]

	static mapping = {
		roles cascade:'delete-orphan'
	}
}
