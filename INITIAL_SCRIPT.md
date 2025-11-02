```sql
CREATE TABLE executed_scripts (
    id NUMBER PRIMARY KEY,
    version VARCHAR(20) NOT NULL,
    script_name VARCHAR(255) NOT NULL,
    executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    app_version VARCHAR(20) NOT NULL,
    success BOOLEAN DEFAULT TRUE,
    error_message TEXT
);
```

```sql
CREATE TABLE schema_version (
    version VARCHAR(20) PRIMARY KEY,
    executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```