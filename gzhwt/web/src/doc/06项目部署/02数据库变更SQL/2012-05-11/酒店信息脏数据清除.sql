DELETE FROM hotel_room_user WHERE  
 hotel_room_id IN
(SELECT room.id FROM hotel_room room WHERE room.meeting_id = 167)
AND user_id NOT IN (SELECT mm.user_id FROM meeting_member mm WHERE mm.meeting_id=167 )
;