db.createUser(
    {
        user:"userFL",
        pwd:"passwordFL",
        roles: [
            {
                role: "readWrite",
                db: "fields_report"
            }
        ]
    }
)